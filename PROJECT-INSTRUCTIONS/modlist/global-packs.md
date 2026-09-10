# Global Packs — 21.0.6

> Fonte canônica desta importação: Notion — `Auditoria Mestre da Modlist — NeoForge 1.21.1`  
> Página-fonte: https://app.notion.com/p/3c369db9f0db81608401fde0abfce4b1  
> Estado no momento da importação: `Instalado — Dossiê completo`  
> Autoridade física: `modlist(4).txt`, 595 mods top-level  
> Importado em: 2026-09-10

## Propriedades do registro

- **Mod:** Global Packs
- **Arquivo JAR:** `globalpacks-neoforge-1.21.1-21.0.6.jar`
- **Versão 1.21.1:** `21.0.6`
- **Categoria:** QoL
- **Decisão:** Sem decisão
- **Estado da pesquisa:** Verificado
- **Estado no pack na origem:** Instalado — Dossiê completo
- **Fonte:** https://www.curseforge.com/minecraft/mc-mods/globalpacks/files/6634585
- **Função:** Carregador global de datapacks e resource packs para todos os mundos da instância, com ordem de prioridade configurável e suporte a packs built-in/required.
- **Dependências:** NeoForge 1.21.1. Não exige outro provider funcional; o efeito concreto depende dos datapacks/resource packs configurados na instância.
- **Compatibilidade/Riscos:** Prioridade pode sobrescrever recipes, tags, loot, worldgen e assets. Riscos adicionais: pack duplicado, built-in ID obsoleto, resource pack obrigatório conflitante, TOML/pack.mcmeta inválido e divergência entre mundo novo/existente.
- **Sobreposição:** Pode sobrepor qualquer datapack/resource pack pelo mecanismo normal de prioridade, mas não se torna owner do conteúdo sobreposto. Avaliar conflitos pelo pack concreto e pela ordem efetiva.
- **Observações:** Release física 21.0.6. Config efetiva `global_packs.toml` e pastas globais da instância não foram fornecidas nesta auditoria; portanto nenhum pack global específico foi presumido como ativo.
- **Procedência:** modlist.txt física atual de 09/09/2026 + release/changelog/documentação oficial Global Packs 21.0.6. Source público vinculado pelo projeto não foi usado como authority de internals por não estar auditavelmente acessível nesta execução.
- **Histórico da decisão:** vazio.
- **Atualização/Status:** PADRÃO ALEX'S MOBS APLICADO EM 09/09/2026 — Global Packs 21.0.6 release-pinned; global data/resource packs, prioridade, force-loading, built-ins, config/reload, riscos e matriz de testes catalogados.
- **Data da última decisão:** 2026-08-26

## Dossiê operacional — padrão Alex's Mobs

> 🔎 **ESCOPO CANÔNICO.** Runtime físico: `globalpacks-neoforge-1.21.1-21.0.6.jar`, mod id `globalpacks`, versão `21.0.6`, NeoForge 1.21.1. A release oficial 21.0.6 e sua documentação são a authority desta ficha; o source público vinculado pelo projeto não estava acessível de forma auditável nesta execução, portanto internals/classes não são inventados.

## 1. Identidade e função
Global Packs automatiza o carregamento de **datapacks e resource packs globais** em todos os mundos da instância. Seu efeito é infraestrutural: ele injeta/força packs no pipeline normal de recursos/dados, em vez de criar diretamente conteúdo de gameplay próprio.

## 2. Authority e impacto transversal
O mod é authority apenas da descoberta, ativação e prioridade dos packs que ele gerencia. O conteúdo final de recipes, tags, loot, worldgen, models, textures e demais recursos continua pertencendo aos datapacks/resource packs carregados e ao sistema vanilla/NeoForge que resolve sobreposição.

## 3. Configuração — `global_packs.toml`
A linha 21.0.x usa `global_packs.toml`. A documentação oficial permite definir pastas e arquivos de datapacks/resource packs. Para Minecraft 1.21+, `<instance>/datapacks/` é uma localização padrão documentada para datapacks globais; caminhos customizados podem ser configurados.

## 4. Prioridade de packs
A ordem na configuração é semanticamente relevante: **o primeiro pack listado possui maior prioridade in-game**. Portanto reordenar entradas pode alterar silenciosamente recipes, tags, loot tables, worldgen, models ou texturas sem mudança de JAR.

## 5. Resource packs required/force-loaded
Resource packs colocados na configuração podem ser force-loaded/required para o jogador. Isso torna o mod parte do handshake/prática de distribuição visual do pack: cliente não deve poder interpretar a ausência de um resource pack obrigatório como estado válido se o servidor/modpack o exige.

## 6. Built-in packs
A 21.0.5 introduziu `resourcepacks.enable_builtin`, lista de IDs de resource packs built-in de mods que devem ser habilitados. A 21.0.6 separou configuração de built-in datapacks e resource packs. Esses IDs precisam ser obtidos do runtime/log ou documentação real; não inferir nomes.

## 7. Logging e diagnóstico
`log_pack_ids`, introduzido na 21.0.5, pode registrar IDs de packs disponíveis e é útil para diagnosticar prioridade e built-in packs. Esse log deve ser preferido a adivinhação quando uma integração depender de um pack específico.

## 8. Mudanças relevantes da 21.0.6
A release física 21.0.6:
- separa configuração de built-in data/resource packs;
- deixa de sobrescrever continuamente a formatação do config;
- permite que packs adicionados por entrada de pasta recebam prioridade inferior quando um arquivo específico é declarado.
A 21.0.3 também corrigiu respeito à ordem do config e duplicação visual de resource packs forçados; a 21.0.2 renomeou a configuração para `global_packs.toml`.

## 9. Formato e descoberta
Packs podem ser ZIP ou diretórios descompactados, desde que tenham estrutura válida e `pack.mcmeta` na posição correta. Erros comuns documentados incluem `pack.mcmeta` aninhado incorretamente e sintaxe TOML inválida.

## 10. Lifecycle / reload
O estado efetivo depende do startup do jogo/servidor, criação/abertura de mundo e resource/datapack reload. Alterar conteúdo ou prioridade de um pack requer validação após reload/restart; caches de recipes/tags/resources não devem ser assumidos atualizados apenas porque o arquivo foi editado em disco.

## 11. Client / server
- **Datapacks:** impactam state/data server-authoritative como recipes, tags, loot e worldgen.
- **Resource packs:** impactam assets/client presentation e podem ser forçados pelo stack configurado.
- **Global Packs:** coordena a ativação; não deve ser tratado como owner do conteúdo interno de cada pack.

## 12. Integrações concretas no modpack
O pack possui grande quantidade de mods com recipes, tags, datapacks e resource packs próprios. Portanto qualquer pack global pode sobrepor múltiplos providers simultaneamente. Nesta auditoria **os arquivos reais de `global_packs.toml` e as pastas globais da instância não foram fornecidos**, então não se afirma quais packs estão efetivamente configurados.

## 13. Riscos técnicos
- prioridade incorreta sobrescrevendo recipe/tag/worldgen esperado;
- pack global duplicando datapack já distribuído por mod/KubeJS;
- resource pack obrigatório conflitando com stack visual do cliente;
- built-in pack habilitado com ID errado ou obsoleto;
- ZIP/diretório com `pack.mcmeta` em nível incorreto;
- config TOML inválido;
- alteração de ordem sem regression test;
- datapack reload produzindo registry/data errors;
- mundo novo e mundo existente divergirem por mudança de worldgen pack.

## 14. Matriz de testes obrigatória
- [ ] Boot de dedicated server com Global Packs 21.0.6.
- [ ] Identificar e registrar o `global_packs.toml` efetivo da instância.
- [ ] Enumerar todos os datapacks/resource packs globais e sua ordem.
- [ ] Confirmar que o primeiro item realmente prevalece em conflito controlado.
- [ ] Validar `log_pack_ids` para built-in packs quando necessário.
- [ ] Resource packs required chegam ao cliente e não aparecem duplicados.
- [ ] `/reload` conclui sem recipe/tag/loot errors.
- [ ] Mundo novo carrega worldgen esperado.
- [ ] Mundo existente reabre sem datapack incompatibility warning inesperado.
- [ ] Comparar recipes/tags críticos antes/depois de qualquer alteração de prioridade.

## 15. Evidências e limites
- **Modlist física:** `globalpacks-neoforge-1.21.1-21.0.6.jar`.
- **Release oficial 21.0.6:** NeoForge 1.21/1.21.1, file ID 6634585.
- **Documentação/changelog oficial:** `global_packs.toml`, ordem de prioridade, force-loading, built-in packs, logging e mudanças 21.0.2–21.0.6.
- **Limite:** configuração/pastas reais da instância não foram auditadas; não é possível declarar quais packs globais estão ativos nem seus conflitos concretos sem esses arquivos.
- **Runtime:** nenhum teste acima foi executado nesta catalogação.
