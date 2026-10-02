# Cold Sweat: Altitude — 0.7.0

> **Adição pós-snapshot físico — 02/10/2026.** O usuário confirmou que adicionou **Cold Sweat: Altitude** ao perfil. O snapshot físico disponível no projeto, `modlist(1).txt` de 16/09/2026, é anterior a essa instalação e ainda não contém o JAR. A release pública atual para Minecraft 1.21.1 / NeoForge é `coldsweat_altitude-0.7.0.jar` / `0.7.0`. Portanto esta ficha documenta **0.7.0 como build-alvo atualmente publicada e relatada como adicionada**, mas não inventa SHA-1, metadata física nem ordem de JAR antes de um novo snapshot.

> **Status de certificação:** **SEM `✅-`**. Não existe ficha canônica correspondente recuperável no Notion e a nova instalação ainda não foi re-fetched por modlist/JAR físico atualizado.

## Propriedades do catálogo

- **Mod:** Cold Sweat: Altitude
- **Arquivo JAR alvo:** `coldsweat_altitude-0.7.0.jar`
- **Versão alvo:** `0.7.0`
- **Mod id:** `coldsweat_altitude`
- **Minecraft / loader:** 1.21.1 / NeoForge
- **Ambiente:** client + server
- **Licença publicada da release:** MIT
- **Categoria:** Addons; Adventure and RPG; Biomes; Dimensions; Utility & QoL
- **Estado no pack:** Adicionado pelo usuário em 02/10/2026; confirmação física detalhada pendente
- **Estado da pesquisa:** Documentação upstream verificada; artefato físico pós-snapshot pendente
- **Decisão:** Manter/adicionar
- **Função:** camada configurável de temperatura por altitude para Cold Sweat, com bandas Y, gradientes, shelter/protection, mensagens de feedback e integração explícita com Create Aeronautics/Sable.
- **Dependência funcional obrigatória:** Cold Sweat. A release pública informa **Cold Sweat 2.4.1+**; o snapshot físico do pack já contém `ColdSweat-2.4.3.1.jar`.
- **Fonte principal:** https://www.curseforge.com/minecraft/mc-mods/cold-sweat-altitude
- **Código-fonte:** https://github.com/sprocketaudio/Cold-Sweat-Altitude
- **Release 0.7.0:** https://www.curseforge.com/minecraft/mc-mods/cold-sweat-altitude/files/8754518

# Dossiê operacional — padrão Alex's Mobs

## 1. Papel e authority

Cold Sweat: Altitude **não substitui o Cold Sweat** e não deve ser tratado como segundo sistema corporal de temperatura. Seu papel é fornecer uma **fonte/modificador ambiental de temperatura baseada em altitude**, que alimenta o sistema existente do Cold Sweat.

No stack do pack:
- **Cold Sweat 2.4.3.1** continua authority da temperatura corporal e das consequências térmicas;
- **Cold Sweat: Altitude** define bandas/gradientes de altitude e seus modificadores ambientais;
- **Create: Cold Sweat 1.1.2** permanece uma integração separada do ecossistema Create com Cold Sweat;
- qualquer lógica própria de Volcanoes, estações ou clima não deve ser confundida com ownership da temperatura corporal.

## 2. Modelo de bandas de altitude

A configuração permite criar várias bandas com:
- `minY` e `maxY`;
- habilitação individual;
- dimensão ou conjunto de dimensões;
- whitelist/blacklist de dimensão;
- modificador de temperatura;
- modo de aplicação;
- prioridade;
- mensagem de entrada/actionbar;
- shelter check;
- raio e redução por abrigo.

A prioridade resolve sobreposição entre bandas. Isso permite modelar, por exemplo:
- cavernas profundas mais quentes;
- superfície relativamente neutra;
- montanhas progressivamente frias;
- atmosfera superior perigosa.

As faixas são **data/config driven**; não presumir os valores do exemplo upstream como balanceamento canônico do pack.

## 3. Modificadores de temperatura

O addon publica dois modelos de modificação:
- **ADD** — adiciona uma parcela ao valor térmico;
- **multiplicativo** — escala a contribuição conforme configuração.

A ficha não transforma esses valores em graus Celsius nem em dano direto. O resultado final pertence ao pipeline do Cold Sweat.

## 4. Gradientes suaves — 0.7.0

A 0.7.0 introduz três modos de transição:
- `NONE` — mudança imediata no limite da banda;
- `LINEAR` — interpolação ao longo da banda;
- `BOUNDARY` — região central mais estável, com transição suave próxima às bordas.

`BOUNDARY` passou a ser o padrão de configs recém-geradas na 0.7.0. Configs antigas podem migrar novos campos sem perder as bandas existentes, mas o autor recomenda regenerar/avaliar a configuração para aproveitar defaults e comentários atualizados.

## 5. Shelter / abrigo

Bandas podem reduzir o efeito de altitude quando o jogador está protegido por abrigo. A configuração expõe:
- habilitação da checagem;
- raio;
- fator de redução.

A 0.7.0 melhora especificamente detecção em:
- espaços parcialmente fechados;
- estruturas/ships montados por Aeronautics/Sable.

Também corrige folhas/folhagem contando indevidamente como cobertura.

**Boundary:** shelter deste addon é uma regra para a contribuição térmica de altitude. Não significa automaticamente “cabine pressurizada”, “oxigênio seguro” ou proteção contra outros hazards.

## 6. Proteção por item/tag

O projeto expõe proteção baseada em tags de itens para modpack makers. Isso permite fazer equipamento específico mitigar contribuição de altitude sem hardcode por nome.

Para perks/quests:
- não inferir proteção apenas por ser “armadura quente”;
- usar a tag/contrato real do addon;
- não duplicar insulation já pertencente ao Cold Sweat sem uma regra explícita.

## 7. Dimensões

Cada banda pode ser limitada por:
- whitelist de dimensão;
- blacklist de dimensão.

Assim, Y alto no Overworld não precisa ter o mesmo significado térmico no Nether, End ou dimensões modded.

O addon fornece a ferramenta; o balanceamento efetivo por dimensão é responsabilidade da configuração do pack.

## 8. Feedback de actionbar

Ao trocar de faixa, o addon pode exibir mensagem colorida:
- quente → vermelho;
- neutro → branco;
- frio → azul.

A 0.7.0 adiciona:
- mensagens tintadas por temperatura;
- fade in/out;
- mensagens para todas as bandas do config padrão;
- `actionbarDisplayTicks` para duração;
- correção das mensagens em Creative.

Esse feedback é apresentação. O texto/cor não é authority para perks; lógica técnica deve consultar o estado real do provider.

## 9. Create Aeronautics / Sable

A integração upstream é explícita. Quando Aeronautics/Sable estão instalados, o addon considera:
- altitude dentro de ships/contraptions;
- shelter de interiores;
- fontes de calor associadas à nave.

O upstream cita **burners de Aeronautics e steam vents** como fontes que podem contribuir calor tanto no world space normal quanto em contraptions Sable.

A 0.7.0 melhora:
- resolução de altitude;
- shelter;
- fontes térmicas;
- interiores de ships parcialmente fechados;
- casos incorretos de shelter/thermal behavior dentro de Sable.

Esse ponto é diretamente relevante porque o pack possui Create Aeronautics e Sable no stack técnico.

## 10. Configuração e comandos

Arquivo server config publicado:
`config/coldsweat_altitude-server.toml`

Comandos publicados:
- `/coldsweat_altitude status` — mostra banda ativa, modificadores, shelter, proteção, coordenadas Sable e fontes térmicas próximas;
- `/coldsweat_altitude reload` — recarrega a configuração em runtime.

A 0.7.0 adiciona também `debugLogging` opcional.

Esses comandos são os principais instrumentos de diagnóstico para QA do modpack.

## 11. Changelog material da 0.7.0

### Adicionado
- gradientes `NONE`, `LINEAR`, `BOUNDARY`;
- `BOUNDARY` como default para novos configs;
- actionbar tintada por temperatura;
- fade das mensagens;
- mensagens default para todas as bandas;
- `actionbarDisplayTicks`;
- `debugLogging`.

### Melhorado
- compatibilidade Aeronautics/Sable de altitude, shelter e heat sources;
- shelter em ships montados/parcialmente fechados;
- saída de `status`;
- comentários e migração de config.

### Corrigido
- shelter/thermal incorreto em alguns interiores Sable;
- folhas/folhagem contando como shelter;
- actionbar ausente em Creative.

## 12. Histórico publicado relevante

A linha pública para 1.21.1 contém 0.1.0 → 0.7.0. Entre os marcos verificáveis:
- **0.6.0:** internacionalização de UI/comandos e suporte a translation keys em mensagens configuráveis;
- **0.6.2:** corrige crash de dedicated server no caminho Sable, shelter Sable no servidor e edge cases de creative flight;
- **0.7.0:** grande revisão de gradientes, feedback, shelter e diagnóstico.

Esta ficha não inventa detalhes de releases cujo changelog não foi necessário para descrever o baseline atual.

## 13. Drift do código-fonte pós-release

O branch `main` do source repository já declara **0.8.1** e contém descrição/metadata de funcionalidades posteriores, incluindo wind exposure/windproof gear.

Isso é **desenvolvimento upstream posterior à release pública 0.7.0**. Não incorporar essas funções ao runtime instalado até existir artefato publicado/instalado correspondente.

A mesma source main atualmente eleva o piso de Cold Sweat para 2.4.3.1; esse valor não é retroativamente atribuído à release 0.7.0, cuja página pública declara 2.4.1+.

## 14. Compatibilidade e overlaps no pack

Pontos principais de interação:
1. **Cold Sweat** — owner do estado corporal; este addon injeta contexto de altitude.
2. **Create Aeronautics/Sable** — integração explícita de altitude/shelter/heat sources.
3. **Create: Cold Sweat** — bridge separado; evitar double counting térmico.
4. **Mods de estações/clima/bioma** — podem alterar temperatura base; não assumir que altitude sobrescreve essas fontes.
5. **Volcanoes** — hazards de atmosfera/pressão/calor próprios devem manter boundaries próprios.

## 15. Riscos operacionais

1. Bandas sobrepostas com prioridade errada.
2. ADD/multiplicativo produzirem extremos térmicos quando combinados com clima/bioma.
3. Abrigo em contraption ser reconhecido diferente após update de Aeronautics/Sable.
4. Dupla aplicação de calor de burners por mais de um bridge.
5. Tags de proteção amplas demais neutralizarem altitude sem intenção.
6. Config antigo migrar sem adotar defaults novos esperados.
7. Client feedback divergir do estado real em latency/reload.
8. Dedicated server carregar classes de integração opcionais incorretamente.
9. Source main 0.8.x ser confundido com release instalada 0.7.0.

## 16. Matriz de validação para esta instância

- [ ] Confirmar JAR físico, mod id, runtime e SHA-1 em novo snapshot.
- [ ] Startup NeoForge 1.21.1 com Cold Sweat 2.4.3.1.
- [ ] Testar bandas abaixo/na borda/no centro/acima dos limites.
- [ ] Testar `NONE`, `LINEAR` e `BOUNDARY`.
- [ ] Testar whitelist/blacklist por dimensão.
- [ ] Testar prioridade entre duas bandas sobrepostas.
- [ ] Testar shelter aberto, parcialmente fechado e completamente fechado.
- [ ] Confirmar que foliage não conta como shelter indevidamente.
- [ ] Testar tags de proteção com/sem item.
- [ ] Verificar actionbar e fade em Survival e Creative.
- [ ] Executar `/coldsweat_altitude status` e conferir banda/modifier/shelter.
- [ ] Executar reload sem restart.
- [ ] Testar em solo, ship Aeronautics e sublevel/contraption Sable.
- [ ] Testar burners/steam vents no mundo e em nave.
- [ ] Dedicated-server smoke.
- [ ] Confirmar ausência de double thermal contribution com Create: Cold Sweat.
- [ ] Verificar logs com `debugLogging` apenas em diagnóstico.

Nenhum teste foi marcado como executado nesta auditoria documental.

## 17. Regras para outros chats

- Não tratar altitude como dano direto; é contribuição térmica para Cold Sweat.
- Não tratar shelter como pressurização/oxigênio.
- Não usar cor da actionbar como receipt de perk.
- Para perks, preferir adapter/query versionado do estado real.
- Para quests, bandas e comandos podem ser usados como objetivos de exploração, mas a conclusão deve observar state/event comprovável.
- Não documentar funcionalidades 0.8.x como presentes enquanto o JAR alvo continuar 0.7.0.
- Atualização futura deve reconciliar primeiro JAR físico e metadata, depois source/changelog.

## 18. Evidências e grau de confiança

**Alta confiança — release pública:** CurseForge confirma 0.7.0 como release atual de 1.21.1/NeoForge, file ID 8754518, ambiente client+server, categorias e dependência Cold Sweat 2.4.1+.

**Alta confiança — comportamento publicado:** página oficial/README descreve bandas, gradientes, shelter, protection, actionbar, Aeronautics/Sable, config e comandos.

**Alta confiança — pack existente:** snapshot físico anterior confirma Cold Sweat 2.4.3.1, Create Aeronautics e o ecossistema Sable já presentes.

**Pendente:** o novo JAR instalado de Cold Sweat: Altitude ainda não foi re-fetched da máquina/perfil; portanto SHA-1, metadata efetivamente carregada e ordem física permanecem não certificados.

> **Boundary canônico:** Cold Sweat é o owner térmico; Cold Sweat: Altitude acrescenta a dimensão de **altitude** ao ambiente, com configuração e compatibilidade de nave, sem substituir o sistema corporal.
