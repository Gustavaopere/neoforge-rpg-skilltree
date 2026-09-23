# Create Aeronautics: Toolgun

> **Autoridade física atual — 22/09/2026.** `modlist(1).txt` contém **587 entradas top-level incluindo o modloader**; este item ocupa a ordem física **#131**: JAR `create_aeronautics_toolgun-0.3.6.jar`, mod id `create_aeronautics_toolgun`, runtime `0.3.6`, SHA-1 `81103160768a52a5bc7fffb5fadc1c441883d5d5`.

## Propriedades do registro

- **Mod:** Create Aeronautics: Toolgun
- **Arquivo JAR:** create_aeronautics_toolgun-0.3.6.jar
- **Versão 1.21.1:** 0.3.6
- **Categoria:** Tecnologia, QoL
- **Função:** Ferramenta de engenharia para veículos físicos do Create Aeronautics, com recursos de manipulação, blueprints e operações sobre estruturas físicas.
- **Dependências:** Create Aeronautics 1.3.2 + Sable 2.0.5 no stack físico. A ferramenta opera sobre estruturas/contraptions físicas; não é provider físico independente.
- **Estado no pack:** Integrado ao Github
- **Estado da pesquisa:** Verificado
- **Compatibilidade/Riscos:** Riscos: blueprint state incompleto, duplicação/remoção indevida de craft, transformação de frame errada, autorização insuficiente, magnet manipulation desync e API drift Aeronautics/Sable.
- **Fonte:** https://www.curseforge.com/minecraft/mc-mods/create-aeronauticstoolgun
- **Procedência:** modlist.txt física atual de 16/09/2026 + runtime `create_aeronautics_toolgun` 0.3.6 + CurseForge/Modrinth oficiais da release 0.3.6 revalidados em 20/09/2026.
- **Observações:** JAR físico `create_aeronautics_toolgun-0.3.6.jar`, mod id `create_aeronautics_toolgun`, runtime 0.3.6. Release oficial NeoForge 1.21.1 de 21/08/2026, Client & Server. 0.3.6 adiciona vehicle radar com scan/player direction/vehicle status e melhora query, preview e printing de veículos grandes.
- **Atualização/Status:** REVALIDADO EM 20/09/2026 — lote físico #130: create_aeronautics_toolgun-0.3.6.jar / 0.3.6 confirmados; vehicle radar clicável e melhorias de query/preview/printing para veículos grandes permanecem atuais.
- **Decisão:** Sem decisão
- **Sobreposição:** Ferramenta de engenharia específica para estruturas físicas Aeronautics; não equivale a ferramentas gerais de construção, schematic ou Carry On.

> 🔧 **ESCOPO CANÔNICO.** Runtime físico `create_aeronautics_toolgun-0.3.6.jar`. É uma ferramenta de engenharia para estruturas físicas Aeronautics, inspirada em workflows de toolgun/blueprint, não um segundo motor de física.

## 1. Papel e authority
Aeronautics/Sable permanecem authority de body, sublevel, transform e physics. Toolgun oferece operações de engenharia sobre esses objetos. Uma operação visualizada pelo cliente só é válida após o servidor confirmar alvo, permissão e transformação.
## 2. Workflow documentado
O projeto descreve um sistema de toolgun/blueprints e magnetic field guns para **salvar, imprimir, deletar e manipular** physical craft. Isso implica duas superfícies distintas: representação persistível de um craft/blueprint e manipulação direta de objetos físicos existentes.
A release **0.3.6** adiciona também um **vehicle radar** que mostra efeitos de scan, direção do jogador e status dos veículos; alvos do radar podem ser clicados para inspecionar e gerenciar veículos. O radar é discovery/management UI: seleção visual não deve substituir validação server-side de ownership, permissão ou existência atual do craft.
A mesma 0.3.6 melhora desempenho de **query, preview e printing** para veículos grandes. Isso reduz custo esperado dessas operações, mas não transforma preview em authority do blueprint nem elimina o risco de state parcial em crafts grandes.
A ficha não inventa modos adicionais não publicados para 0.3.6.
## 3. Blueprint e integridade
Salvar craft deve capturar apenas state que o provider considera serializável. Imprimir/recriar não pode duplicar inventories, fluids, contraption-bound identities ou links externos por cópia cega. Se a ferramenta exclui/remove uma estrutura, a remoção deve ser atômica em relação ao state físico.
## 4. Magnetic manipulation
Manipulação de craft em runtime precisa operar no frame correto e respeitar massa/constraints do provider. Input do cliente é intenção; transform final e collision precisam convergir no servidor. Em multiplayer, dois operadores não podem controlar simultaneamente o mesmo objeto sem resolução determinística.
## 5. Permissions e segurança
A ferramenta pode ter poder destrutivo/construtivo elevado. Claims, ownership e regras de servidor devem ser respeitados. Não permitir que blueprint/delete/manipulate contorne proteção apenas porque a ação não é um break vanilla.
## 6. Lifecycle
Testar save→reload→print, chunk unload, restart, dimension/sublevel transition e craft parcialmente carregado. Referências a entities/sublevels não devem ser persistidas como IDs recicláveis sem validação.
## 7. Riscos
1. Blueprint duplica inventory/fluid/data components.
2. Delete remove apenas parte do craft e deixa state órfão.
3. Print cria links UUID/references duplicados.
4. Magnetic manipulation usa frame incorreto.
5. Dois players disputam o mesmo craft.
6. Tool action ignora claims/permissões.
7. Radar exibe alvo stale ou permite ação de management sem revalidar o craft no servidor.
8. Query/preview/printing de veículo grande degrada performance ou converge para state parcial.
9. API drift Aeronautics/Sable quebra serialization ou transforms.
## 8. Boundary para quests/perks
Usar a Toolgun ou abrir blueprint não prova construção concluída. Progressão deve observar resultado server-side deduplicável; debug/manipulation administrativa não deve conceder XP por padrão.
## 9. Matriz de testes
- [ ] Dedicated server inicia com Toolgun 0.3.6 + Aeronautics 1.3.2.
- [ ] Save/print de craft simples preserva blocos/state sem dupe.
- [ ] Craft com inventory/fluid não duplica conteúdo.
- [ ] Delete remove exatamente o alvo autorizado.
- [ ] Manipulation funciona world→ship e ship→world sem frame jump.
- [ ] Dois jogadores não produzem controle concorrente incoerente.
- [ ] Restart não invalida blueprint de forma silenciosa.
- [ ] Proteções/claims relevantes não são contornadas.
Nenhum teste foi marcado como aprovado.
## 10. Evidências e limite
A publicação oficial confirma a Release 0.3.6, o escopo toolgun/blueprint/magnetic manipulation, o vehicle radar clicável e as melhorias de query/preview/printing para veículos grandes. O projeto público não fornece nesta evidência um catálogo exato de todos os modos/configs da build; esses detalhes permanecem fora da ficha até source/runtime específico.
