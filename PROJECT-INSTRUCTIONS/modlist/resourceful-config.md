# Resourceful Config

- **Banco de origem:** Auditoria Mestre da Modlist — NeoForge 1.21.1
- **Página Notion:** https://app.notion.com/p/3c369db9f0db81cc813bcb7b43d85dae
- **Estado no pack na exportação:** Instalado — Dossiê completo
- **Autoridade física usada:** `modlist 08.09.2026.txt` / `modlist.txt` — `resourcefulconfig-neoforge-1.21-3.0.11.jar`, mod id `resourcefulconfig`, runtime `3.0.11`
- **Data da exportação:** 2026-09-11

## Divergência documental detectada na exportação

- A página Notion declara procedência por uma “modlist.txt física canônica de 10/09/2026”. A autoridade física mais recente realmente acessível nesta execução permanece o snapshot de **08/09/2026 com 595 top-levels**. Nesse snapshot, Resourceful Config 3.0.11 está presente. O corpo-fonte abaixo é preservado sem reescrita silenciosa.

## Propriedades do banco

- **Mod:** Resourceful Config
- **Arquivo JAR:** `resourcefulconfig-neoforge-1.21-3.0.11.jar`
- **Versão 1.21.1:** 3.0.11
- **Estado no pack:** Instalado — Dossiê completo
- **Estado da pesquisa:** Verificado
- **Decisão:** Sem decisão
- **Categoria:** Biblioteca
- **Função:** Biblioteca/API cross-platform de configuração para mods consumidores; fornece infraestrutura de config sem gameplay autônomo.
- **Dependências:** NeoForge 1.21/1.21.1. Consumidores concretos do pack não foram causalmente enumerados nesta etapa; não remover até mapear dependências reais.
- **Sobreposição:** Não é duplicata de Resourceful Lib nem substituível automaticamente por Cloth/Fzzy Config. O contrato de API escolhido por cada consumidor determina a necessidade.
- **Compatibilidade/Riscos:** Riscos: consumer dependency desconhecida, stale config/cache, client/server divergence, listener duplication, schema/version drift e regressão do memory leak de dedicated-server configs corrigido na 3.0.11.
- **Observações:** Release 3.0.11 para NeoForge 1.21/1.21.1, Client & Server. Changelog exato: fix de memory leak em dedicated server configs. Source default atual representa gerações posteriores e não foi tratado como pin binário 3.0.11.
- **Procedência:** modlist.txt física canônica de 10/09/2026 + publicação/changelog oficial Resourceful Config 3.0.11 + repositório oficial usado apenas para arquitetura cross-platform, não como source pin.
- **Fonte:** https://www.curseforge.com/minecraft/mc-mods/resourceful-config
- **Atualização/Status:** PADRÃO ALEX'S MOBS APLICADO EM 10/09/2026 — Resourceful Config 3.0.11 reconstruído: papel de config API, authority do consumidor, dedicated-server leak fix, lifecycle/sync, sobreposição, riscos e testes.
- **Histórico da decisão:**
- **Data da última decisão:** 2026-08-27

# Dossiê operacional — padrão Alex's Mobs

> **ESCOPO CANÔNICO.** Runtime físico: `resourcefulconfig-neoforge-1.21-3.0.11.jar`, mod id `resourcefulconfig`, versão `3.0.11`, NeoForge 1.21/1.21.1. Resourceful Config é uma **biblioteca/API de configuração para desenvolvedores**; não adiciona gameplay autônomo. A release instalada corrige explicitamente um memory leak relacionado a configs de dedicated server.

## 1. Identidade e papel
- **Mod:** Resourceful Config.
- **JAR:** `resourcefulconfig-neoforge-1.21-3.0.11.jar`.
- **Mod id:** `resourcefulconfig`.
- **Versão instalada:** `3.0.11`.
- **Loader/jogo:** NeoForge; release publicada para 1.21/1.21.1.
- **Canal:** Release.
- **Ambiente:** Client & Server.
- **Licença/projeto:** biblioteca open-source da Team Resourceful.

## 2. Papel no modpack
Fornece infraestrutura compartilhada para mods consumidores definirem/expor/sincronizarem configurações de forma cross-platform. A biblioteca não deve ser avaliada como conteúdo: sua utilidade depende de consumidores que chamam sua API.

A remoção segura só pode ser decidida após mapear dependências reais; ausência de gameplay próprio não significa redundância.

## 3. Autoridade / ownership
- **Resourceful Config:** contrato/API de configuração que seus consumidores usam.
- **Mod consumidor:** continua authority do significado de cada opção e do comportamento que ela controla.
- **NeoForge/ambiente de servidor:** fornece lifecycle/plataforma onde configs são carregadas.

A biblioteca não deve reimplementar lógica de gameplay do consumidor nem ser tratada como substituível por Cloth Config/Fzzy Config sem suporte explícito do mod consumidor.

## 4. Superfície técnica confirmada
A documentação oficial classifica o projeto como biblioteca cross-platform para configs. O repositório público possui organização common/Fabric/NeoForge, mas o branch/default atual representa gerações posteriores e **não é usado como source pin exato da 3.0.11**.

Por fail-closed, esta ficha não inventaria classes/métodos/annotations específicos da 3.0.11 sem tag/commit correspondente.

## 5. Delta exato da 3.0.11
O changelog oficial da build instalada registra: **fix memory leak in dedicated server configs**.

Esse delta é operacionalmente relevante porque indica que o lifecycle de configs de dedicated server já teve problema de retenção na linha 3.0.x. Testes devem observar reload/restart e criação/descarte de contextos de servidor sem crescimento indevido.

## 6. Configuração e dados
A biblioteca existe para descrever/carregar configs dos consumidores. Sem abrir os arquivos de cada consumidor, não é correto presumir:
- quais configs do pack usam Resourceful Config;
- quais são client/common/server;
- nomes de arquivos;
- defaults;
- sync policy.

Esses elementos devem ser catalogados na ficha do **mod consumidor** quando identificados.

## 7. Client / Server
A release é Client & Server. Isso não significa que toda config seja sincronizada ou exista nos dois lados; apenas que a biblioteca possui uso válido em ambos os ambientes.

O servidor deve continuar authority de opções que controlam gameplay. Um cliente não deve alterar funcionalidade server-side por editar apenas uma preferência local.

## 8. Lifecycle
Superfícies obrigatórias de validação para uma biblioteca de config:
- mod construction/bootstrap;
- world/server start;
- dedicated server start/stop;
- login/reconnect quando config é sincronizada por consumidor;
- config load/save;
- reload quando suportado pelo consumidor;
- server restart;
- remoção de world/server context;
- encerramento do cliente.

A regressão de memory leak 3.0.11 torna descarte de contextos de servidor um gate explícito.

## 9. Multiplayer
Config de servidor precisa ser consistente para todos os jogadores quando controla gameplay. Se algum consumidor sincroniza valores, o teste deve verificar que:
- cliente novo recebe state correto;
- valor antigo não permanece em cache;
- reconnect não duplica listeners;
- um cliente não sobrescreve config global sem autorização.

A biblioteca não foi assumida como responsável por uma política específica de sync sem source pin.

## 10. Integração com a modlist
A presença top-level está confirmada, mas **os consumidores atuais não foram causalmente enumerados nesta etapa**. Portanto a decisão permanece Sem decisão e a ficha não marca automaticamente Resourceful Config como dependência crítica de um mod específico sem evidência.

Outras bibliotecas de config presentes no pack podem coexistir porque cada consumidor compila/depende do contrato que escolheu.

## 11. Sobreposição
Resourceful Config não é equivalente a **Resourceful Lib**: esta entrada é focada em configuração; Resourceful Lib é uma biblioteca geral separada.

Também não é removível apenas porque Cloth Config ou Fzzy Config estão instalados. APIs de configuração não são drop-in replacements sem suporte do consumidor.

## 12. Riscos técnicos
1. **Consumer dependency unknown:** remover top-level antes de mapear dependentes pode impedir boot.
2. **Dedicated-server memory leak:** área corrigida especificamente na 3.0.11.
3. **Stale config/cache:** valor antigo continua após reload/restart.
4. **Client/server divergence:** gameplay usa valores diferentes entre lados.
5. **Listener duplication:** reload/reconnect registra callback mais de uma vez.
6. **Version drift:** consumidor compilado contra outra geração de API.
7. **Config migration:** mudança de schema/default entre versões.

## 13. Matriz de testes
- [ ] Dedicated server inicia com Resourceful Config 3.0.11.
- [ ] Cliente inicia e conecta sem missing-class/method relacionado à biblioteca.
- [ ] Mod consumidor conhecido consegue ler sua configuração.
- [ ] Alteração válida é persistida conforme comportamento do consumidor.
- [ ] Restart recarrega o valor esperado, sem stale cache.
- [ ] Reconnect não duplica listeners ou aplica config duas vezes.
- [ ] Stress de start/stop/reload não evidencia regressão do memory leak corrigido na 3.0.11.
- [ ] Cliente com valor local divergente não altera gameplay server-side indevidamente.
- [ ] Remoção experimental da biblioteca só é tentada após mapear consumidores e deve falhar/funcionar conforme dependências reais, nunca por suposição.

Nenhum teste foi marcado como aprovado nesta auditoria documental.

## 14. Evidências e limites
- Modlist física: JAR/mod id/runtime exatos.
- Publicação oficial: Release NeoForge 1.21/1.21.1, Client & Server e função de config library.
- Changelog 3.0.11: fix de memory leak em dedicated server configs.
- Repositório oficial: arquitetura cross-platform usada apenas como contexto de projeto, não como pin exato da 3.0.11.
- **Limite:** consumidores top-level não foram causalmente mapeados nesta ficha; nenhuma classe/método/config key foi inventada.
